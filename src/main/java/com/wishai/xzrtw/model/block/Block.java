package com.wishai.xzrtw.model.block;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wishai.xzrtw.model.gui.blocks.BlockEle;

import javax.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "_type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ImageBlock.class, name = "image"),
        @JsonSubTypes.Type(value = ParagraphBlock.class, name = "paragraph")
})
public abstract class Block {
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "BlockGenerator")
    @TableGenerator(
            name = "BlockGenerator",
            table = "pk_generator",
            pkColumnName = "gen_name",
            valueColumnName = "gen_value",
            pkColumnValue = "block_pk",
            initialValue = 0,
            allocationSize = 1
    )
    private Integer id;
    private String dType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getdType() {
        return dType;
    }

    public void setdType(String dType) {
        this.dType = dType;
    }

    /* CUSTOMIZE FUNCTIONS */

    public abstract BlockEle toBlockEle();
}
