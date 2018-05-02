package com.wishai.xzrtw.service

import com.wishai.xzrtw.exception.FileNotFound
import com.wishai.xzrtw.exception.FileNotStore
import com.wishai.xzrtw.model.*
import com.wishai.xzrtw.model.block.Block
import com.wishai.xzrtw.model.block.ImageBlock
import com.wishai.xzrtw.model.block.ParagraphBlock
import com.wishai.xzrtw.model.src.TextSrc
import com.wishai.xzrtw.repository.*
import com.wishai.xzrtw.repository.src.TextSrcRepository
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.ArrayList

@Service
class ResourceServiceImpl @Autowired
constructor(
        private val textSrcRepository: TextSrcRepository,
        private val srcCacheRepository: SrcCacheRepository,
        private val fileRepository: FileRepository,
        private val textRepository: TextRepository,
        private val articleRepository: ArticleRepository,
        private val applicantRepository: ApplicantRepository
) : ResourceService {


    /* THE FUNCTIONS OF PURE SRC GOES HERE */


    @Throws(FileNotFound::class)
    private fun loadFileSrc(srcKey: String?, type: String): Resource {
        /* get the file path first */
        val filePath = ResourceService.file2Path(type, srcKey)
        try {
            assert(filePath != null)
            val resource = UrlResource(filePath!!.toUri())
            return if (resource.exists() || resource.isReadable) {
                resource
            } else {
                throw FileNotFound()
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

        throw FileNotFound()
    }

    private fun loadTextSrc(srcKey: Int?): String {
        val textSrc = textSrcRepository.findById(srcKey!!).orElse(null)
        return textSrc.src
    }

    @Throws(FileNotStore::class)
    private fun storeFileSrc(file: MultipartFile, dType: String): String {
        // get the file name as the srcKey
        val srcKey = Paths.get(file.originalFilename).fileName.toString()
        // throw the exception if not found file
        if (file.isEmpty) {
            throw FileNotStore()
        }

        var filePath = ResourceService.file2Path(dType, srcKey)

        // throw exception if file name exists
        // !! should be renamed in the future
        var nameCount = 0
        assert(filePath != null)
        while (Files.exists(filePath)) {
            // throw the exception if trying too many times
            if (nameCount > 10000) {
                throw FileNotStore()
            }

            // rename the file
            filePath = ResourceService.file2Path(dType, srcKey + nameCount)

            nameCount++
        }

        // then upload the file to certain dir
        try {
            Files.copy(file.inputStream, filePath)
        } catch (e: IOException) {
            e.printStackTrace()
            throw FileNotStore()
        }

        // return the srcKey (file name)
        return srcKey
    }

    private fun storeTextSrc(text: String): Int? {
        var textSrc = TextSrc()
        textSrc.src = text
        textSrc = textSrcRepository.saveAndFlush(textSrc)
        return textSrc.srcKey
    }

    @Throws(FileNotStore::class)
    override fun storeSrc(src: Any, dType: String): Int? {
        /* switch each different type */
        val srcKey: String
        when (dType) {
            ResourceService.FILE_TYPE_IMAGE, ResourceService.FILE_TYPE_VIDEO, ResourceService.FILE_TYPE_MUSIC, ResourceService.FILE_TYPE_PDF, ResourceService.FILE_TYPE_ZIP -> {
                /* store the src, get the srcKey */
                srcKey = storeFileSrc(src as MultipartFile, dType)

                /* create a new data object*/
                var fileCache = SrcCache()
                fileCache.srcKey = srcKey
                fileCache = srcCacheRepository.saveAndFlush(fileCache)

                // return the cache id
                return fileCache.id
            }
            ResourceService.SRC_TYPE_TEXT -> {
                /* store the src, get the srcKey */
                srcKey = storeTextSrc(src as String)!!.toString()

                /* create a new data object, return the id */
                var textCache = SrcCache()
                textCache.srcKey = srcKey
                textCache = srcCacheRepository.saveAndFlush(textCache)

                // return the id
                return textCache.id
            }
        }
        return null
    }

    override fun loadSrc(id: Int, dType: String): Any? {
        return loadSrc(id, dType, false)
    }

    override fun loadSrc(id: Int, dType: String, isCached: Boolean): Any? {
        var src: Any? = null

        var srcKey: String? = null
        if (isCached) {
            val srcCache = srcCacheRepository.findById(id).orElse(null)
            srcKey = srcCache.srcKey
        }
        /* switch each different type */
        when (dType) {
        /* for all the file type */
            ResourceService.FILE_TYPE_IMAGE, ResourceService.FILE_TYPE_VIDEO, ResourceService.FILE_TYPE_MUSIC, ResourceService.FILE_TYPE_PDF, ResourceService.FILE_TYPE_ZIP -> {
                /* get the data object */
                if (srcKey == null) {
                    val file = fileRepository.findById(id).orElse(null)
                    srcKey = file.srcKey
                }
                try {
                    src = loadFileSrc(srcKey, dType)
                } catch (fileNotFound: FileNotFound) {
                    fileNotFound.printStackTrace()
                }

            }
            ResourceService.SRC_TYPE_TEXT -> {
                /* get the data object */
                if (srcKey == null) {
                    val text = textRepository.findById(id).orElse(null)
                    srcKey = text.srcKey!!.toString()
                }
                src = loadTextSrc(Integer.parseInt(srcKey))
            }
        }
        return src
    }


    /* METHODS FOR DATA OBJECTS GOES HERE */

    private fun makeFile(jsonObject: JSONObject): File {
        val file = File()
        file.type = jsonObject.getString("_type")

        // set the srcKey
        val cacheId = jsonObject.getInt("cacheId")
        val srcCache = srcCacheRepository.findById(cacheId).orElse(null)
        val srcKey = srcCache.srcKey
        file.srcKey = srcKey

        // remove the src cache
        srcCacheRepository.deleteById(cacheId)

        return file
    }

    private fun cacheIdToSrcKey(cacheId: Int): String {
        // find the srcKey by cacheId in srcCache table
        val srcCache = srcCacheRepository.findById(cacheId).orElse(null)
        val srcKey = srcCache.srcKey

        // remove the srcCache record
        srcCacheRepository.deleteById(cacheId)

        return srcKey
    }

    private fun makeText(jsonObject: JSONObject): Text {
        val text = Text()

        // set the srcKey
        val cacheId = jsonObject.getInt("cacheId")
        val srcCache = srcCacheRepository.findById(cacheId).orElse(null)
        val srcKey = srcCache.srcKey
        text.srcKey = Integer.parseInt(srcKey)

        // remove the src cache
        srcCacheRepository.deleteById(cacheId)

        return text
    }


    override fun makeArticleContent(data: Map<String, Any>): ArticleContent {
        // initialize an article content
        val articleContent = ArticleContent()

        // set single non-json data
        articleContent.title = data["title"] as String
        articleContent.info = data["info"] as String
        // set the cover
        articleContent.cover = makeFile(data["coverJson"] as JSONObject)
        // set the blocks
        val blocks = ArrayList<Block>()

        val blocksJson = data["blocksJson"] as JSONArray
        for (jsonObject in blocksJson) {
            val blockJson = jsonObject as JSONObject
            // switch the type of the blocks
            var block: Block? = null
            when (blockJson.getString("_type")) {
                ResourceService.BLOCK_TYPE_IMAGE -> {
                    val imageBlock = ImageBlock()
                    imageBlock.info = blockJson.getString("info")
                    // set the file
                    val file = makeFile(blockJson.getJSONObject("file"))
                    imageBlock.file = file
                    block = imageBlock
                }
                ResourceService.BLOCK_TYPE_PARAGRAPH -> {
                    val paragraphBlock = ParagraphBlock()
                    // set the text
                    val text = makeText(blockJson.getJSONObject("text"))
                    paragraphBlock.text = text
                    block = paragraphBlock
                }
            }
            assert(block != null)
            block!!.setdType(blockJson.getString("_type"))
            // push the blocks into the list
            blocks.add(block)
        }
        // set the block
        articleContent.blocks = blocks

        return articleContent
    }

    override fun findArticle(id: Int?): Article? {
        return articleRepository.findById(id!!).orElse(null)
    }

    override fun saveArticle(article: Article): Int? {
        var article = article
        // convert the cacheId to srcKey in File, Text and other src objects
        val blocks = article.articleContentZh.blocks
        for (block in blocks) {
            when (block.getdType()) {
                ResourceService.BLOCK_TYPE_IMAGE -> {
                    // src: a file
                    val file = (block as ImageBlock).file
                    file.srcKey = cacheIdToSrcKey(file.cacheId)
                }
                ResourceService.BLOCK_TYPE_PARAGRAPH -> {
                    // src: a text
                    val text = (block as ParagraphBlock).text
                    text.srcKey = Integer.valueOf(cacheIdToSrcKey(text.cacheId))
                }
            }
        }

        // convert the cacheId to srcKey in cover
        val cover = article.articleContentZh.cover
        cover.srcKey = cacheIdToSrcKey(cover.cacheId)

        article = articleRepository.saveAndFlush(article)
        return article.id
    }

    override fun saveApplicant(applicant: Applicant): Int? {
        var applicant = applicant
        applicant = applicantRepository.saveAndFlush(applicant)

        return applicant.id
    }

    override fun removeArticle(id: Int?) {
        articleRepository.deleteById(id!!)
    }

}

