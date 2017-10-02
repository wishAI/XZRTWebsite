package com.wishai.xzrtw.service;

import com.wishai.xzrtw.exception.FileNotFound;
import com.wishai.xzrtw.exception.FileNotStore;
import com.wishai.xzrtw.model.*;
import com.wishai.xzrtw.model.block.Block;
import com.wishai.xzrtw.model.block.ImageBlock;
import com.wishai.xzrtw.model.block.ParagraphBlock;
import com.wishai.xzrtw.model.src.TextSrc;
import com.wishai.xzrtw.repository.*;
import com.wishai.xzrtw.repository.src.TextSrcRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final TextSrcRepository textSrcRepository;


    private final SrcCacheRepository srcCacheRepository;

    private final FileRepository fileRepository;

    private final TextRepository textRepository;


    private final ArticleRepository articleRepository;

    private final ApplicantRepository applicantRepository;

    @Autowired
    public ResourceServiceImpl(
            TextSrcRepository textSrcRepository,
            SrcCacheRepository srcCacheRepository,
            FileRepository fileRepository,
            TextRepository textRepository,
            ArticleRepository articleRepository,
            ApplicantRepository applicantRepository
    ) {
        this.textSrcRepository = textSrcRepository;
        this.srcCacheRepository = srcCacheRepository;
        this.fileRepository = fileRepository;
        this.textRepository = textRepository;
        this.articleRepository = articleRepository;
        this.applicantRepository = applicantRepository;
    }


    /* THE FUNCTIONS OF PURE SRC GOES HERE */


    private Resource loadFileSrc(String srcKey, String type) throws FileNotFound {
        /* get the file path first */
        Path filePath = ResourceService.file2Path(type, srcKey);
        try {
            assert filePath != null;
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFound();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        throw new FileNotFound();
    }

    private String loadTextSrc(Integer srcKey) {
        return textSrcRepository.findOne(srcKey).getSrc();
    }

    private String storeFileSrc(MultipartFile file, String dType) throws FileNotStore {
        // get the file name as the srcKey
        String srcKey = Paths.get(file.getOriginalFilename()).getFileName().toString();
        // throw the exception if not found file
        if (file.isEmpty()) {
            throw new FileNotStore();
        }

        Path filePath = ResourceService.file2Path(dType, srcKey);

        // throw exception if file name exists
        // !! should be renamed in the future
        int nameCount = 0;
        assert filePath != null;
        while(Files.exists(filePath)) {
            // throw the exception if trying too many times
            if(nameCount > 10000) {
                throw new FileNotStore();
            }

            // rename the file
            filePath = ResourceService.file2Path(dType, srcKey + nameCount);

            nameCount ++;
        }

        // then upload the file to certain dir
        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileNotStore();
        }

        // return the srcKey (file name)
        return srcKey;
    }

    private Integer storeTextSrc(String text) {
        TextSrc textSrc = new TextSrc();
        textSrc.setSrc(text);
        textSrc = textSrcRepository.saveAndFlush(textSrc);
        return textSrc.getSrcKey();
    }

    @Override
    public Integer storeSrc(Object src, String dType) throws FileNotStore {
        /* switch each different type */
        String srcKey;
        switch(dType) {
            case FILE_TYPE_IMAGE:
            case FILE_TYPE_VIDEO:
            case FILE_TYPE_MUSIC:
            case FILE_TYPE_PDF:
            case FILE_TYPE_ZIP:
                /* store the src, get the srcKey */
                srcKey = storeFileSrc((MultipartFile) src, dType);

                /* create a new data object*/
                SrcCache fileCache = new SrcCache();
                fileCache.setSrcKey(srcKey);
                fileCache = srcCacheRepository.saveAndFlush(fileCache);

                // return the cache id
                return fileCache.getId();
            case SRC_TYPE_TEXT:
                /* store the src, get the srcKey */
                srcKey = storeTextSrc((String) src).toString();

                /* create a new data object, return the id */
                SrcCache textCache = new SrcCache();
                textCache.setSrcKey(srcKey);
                textCache = srcCacheRepository.saveAndFlush(textCache);

                // return the id
                return textCache.getId();
        }
        return null;
    }

    @Override
    public Object loadSrc(Integer id, String dType) {
        return loadSrc(id, dType, false);
    }

    @Override
    public Object loadSrc(Integer id, String dType, boolean isCached) {
        Object src = null;

        String srcKey = null;
        if(isCached) {
            srcKey = srcCacheRepository.findOne(id).getSrcKey();
        }
        /* switch each different type */
        switch(dType) {
            /* for all the file type */
            case FILE_TYPE_IMAGE:
            case FILE_TYPE_VIDEO:
            case FILE_TYPE_MUSIC:
            case FILE_TYPE_PDF:
            case FILE_TYPE_ZIP:
                /* get the data object */
                if(srcKey == null) {
                    srcKey = fileRepository.findOne(id).getSrcKey();
                }
                try {
                    src = loadFileSrc(srcKey, dType);
                } catch (FileNotFound fileNotFound) {
                    fileNotFound.printStackTrace();
                }
                break;
            case SRC_TYPE_TEXT:
                /* get the data object */
                if(srcKey == null) {
                    srcKey = textRepository.findOne(id).getSrcKey().toString();
                }
                src = loadTextSrc(Integer.parseInt(srcKey));
                break;
        }
        return src;
    }


    /* METHODS FOR DATA OBJECTS GOES HERE */

    private File makeFile(JSONObject jsonObject) {
        File file = new File();
        file.setType(jsonObject.getString("_type"));

        // set the srcKey
        int cacheId = jsonObject.getInt("cacheId");
        String srcKey = srcCacheRepository.findOne(cacheId).getSrcKey();
        file.setSrcKey(srcKey);

        // remove the src cache
        srcCacheRepository.delete(cacheId);

        return file;
    }

    private String cacheIdToSrcKey(int cacheId) {
        // find the srcKey by cacheId in srcCache table
        String srcKey = srcCacheRepository.findOne(cacheId).getSrcKey();

        // remove the srcCache record
        srcCacheRepository.delete(cacheId);

        return srcKey;
    }

    private Text makeText(JSONObject jsonObject) {
        Text text = new Text();

        // set the srcKey
        int cacheId = jsonObject.getInt("cacheId");
        String srcKey = srcCacheRepository.findOne(cacheId).getSrcKey();
        text.setSrcKey(Integer.parseInt(srcKey));

        // remove the src cache
        srcCacheRepository.delete(cacheId);

        return text;
    }


    @Override
    public ArticleContent makeArticleContent(Map<String, Object> data) {
        // initialize an article content
        ArticleContent articleContent = new ArticleContent();

        // set single non-json data
        articleContent.setTitle((String) data.get("title"));
        articleContent.setInfo((String) data.get("info"));
        // set the cover
        articleContent.setCover(makeFile((JSONObject) data.get("coverJson")));
        // set the blocks
        List<Block> blocks = new ArrayList<Block>();

        JSONArray blocksJson = (JSONArray) data.get("blocksJson");
        for(Object jsonObject : blocksJson) {
            JSONObject blockJson = (JSONObject) jsonObject;
            // switch the type of the blocks
            Block block = null;
            switch (blockJson.getString("_type")) {
                case BLOCK_TYPE_IMAGE:
                    ImageBlock imageBlock = new ImageBlock();
                    imageBlock.setInfo(blockJson.getString("info"));
                    // set the file
                    File file = makeFile(blockJson.getJSONObject("file"));
                    imageBlock.setFile(file);
                    block = imageBlock;
                    break;
                case BLOCK_TYPE_PARAGRAPH:
                    ParagraphBlock paragraphBlock = new ParagraphBlock();
                    // set the text
                    Text text = makeText(blockJson.getJSONObject("text"));
                    paragraphBlock.setText(text);
                    block = paragraphBlock;
                    break;
            }
            assert block != null;
            block.setdType(blockJson.getString("_type"));
            // push the blocks into the list
            blocks.add(block);
        }
        // set the block
        articleContent.setBlocks(blocks);

        return articleContent;
    }

    @Override
    public Article findArticle(Integer id) {
        return articleRepository.findOne(id);
    }

    @Override
    public Integer saveArticle(Article article) {
        // convert the cacheId to srcKey in File, Text and other src objects
        List<Block> blocks = article.getArticleContentZh().getBlocks();
        for(Block block : blocks) {
            switch (block.getdType()) {
                case BLOCK_TYPE_IMAGE:
                    // src: a file
                    File file = ((ImageBlock) block).getFile();
                    file.setSrcKey(cacheIdToSrcKey(file.getCacheId()));
                    break;
                case BLOCK_TYPE_PARAGRAPH:
                    // src: a text
                    Text text = ((ParagraphBlock) block).getText();
                    text.setSrcKey(Integer.valueOf(cacheIdToSrcKey(text.getCacheId())));
                    break;
            }
        }

        // convert the cacheId to srcKey in cover
        File cover = article.getArticleContentZh().getCover();
        cover.setSrcKey(cacheIdToSrcKey(cover.getCacheId()));

        article = articleRepository.saveAndFlush(article);
        return article.getId();
    }

    @Override
    public Integer saveApplicant(Applicant applicant) {
        applicant = applicantRepository.saveAndFlush(applicant);

        return applicant.getId();
    }

    @Override
    public void removeArticle(Integer id) {
        articleRepository.delete(id);
    }

}

