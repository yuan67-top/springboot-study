package top.yuan67.webapp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;


/**
 * ES文档
 * @author NieQiang
 * @date 2022-12-15 14:58
 */
@Document(indexName = "books")
public class Book {
  private static final long serialVersionUID = 1L;
  
  /**
   * 主键
   */
  @Id
  private Long id;
  
  /**
   * 编号
   */
  @Field(name = "code", index = false, type = FieldType.Keyword)
  private String code;
  
  /**
   * 题名
   */
  @Field(name = "title", type = FieldType.Keyword, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
  private String title;
  
  /**
   * 主要责任者
   */
  @Field(name = "main_responsible_person",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String mainResponsiblePerson;
  
  /**
   * 版本类别
   */
  @Field(name = "version_category",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String versionCategory;
  
  /**
   * 年代
   */
  @Field(name = "years",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String years;
  
  /**
   * 批校题跋者
   */
  @Field(name = "reviewer_of_postscript",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String reviewerOfPostscript;
  
  /**
   * 藏印
   */
  @Field(name = "tibetan_seal",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String tibetanSeal;
  
  /**
   * 板框
   */
  @Field(name = "plate_frame",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String plateFrame;
  
  /**
   * 合订状态
   */
  @Field(name = "binding_status",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String bindingStatus;
  
  /**
   * 馆藏机构
   */
  @Field(name = "collection_organization",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String collectionOrganization;
  
  /**
   * 卷名（卷次）
   */
  @Field(name = "volume_name",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String volumeName;
  
  /**
   * 存缺卷
   */
  @Field(name = "inventory_volume",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String inventoryVolume;
  
  /**
   * 开本
   */
  @Field(name = "folio",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String folio;
  
  /**
   * 标引填表人
   */
  @Field(name = "indexing_preparer",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String indexingPreparer;
  
  /**
   * 索书号
   */
  @Field(name = "call_number",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String callNumber;
  
  /**
   * 加工记录号
   */
  @Field(name = "processing_record_no",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String processingRecordNo;
  
  /**
   * 普查编号
   */
  @Field(name = "version_category",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String censusNo;
  
  /**
   * 分类
   */
  @Field(name = "classification",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String classification;
  
  /**
   * 子分类
   */
  @Field(name = "subcategory",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String subcategory;
  
  /**
   * 元数据模板
   */
  @Field(name = "metadata_template",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String metadataTemplate;
  
  /**
   * 主从
   */
  @Field(name = "master_slave",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String masterSlave;
  
  /**
   * 主书ID
   */
  @Field(name = "master_book_id",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String masterBookId;
  
  /**
   * 册数
   */
  @Field(name = "number_of_copies",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String numberOfCopies;
  
  /**
   * 册号
   */
  @Field(name = "volume_no",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String volumeNo;
  
  /**
   * 图片路径
   */
  @Field(index = false, type = FieldType.Keyword)
  private String picturePath;
  
  /**
   * 专题文献
   */
  @Field(name = "monographic_literature",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String monographicLiterature;
  
  /**
   * 文献类型
   */
  @Field(name = "document_type",type = FieldType.Keyword, analyzer = "ik_max_word",
      searchAnalyzer = "ik_max_word")
  private String documentType;
  
  /**
   * 描述
   */
  private String descs;
  
  /**
   * IP
   */
  private String ip;
  
  private Date createTime;
  
  /**
   * 创建者id
   */
  private Long createrId;
  
  /**
   * 0=false=未删除,1=true=删除
   */
  private Integer whetherDelete;
  
  /**
   * 排序
   */
  private Integer index;
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getId() {
    return id;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getTitle() {
    return title;
  }
  
  public void setMainResponsiblePerson(String mainResponsiblePerson) {
    this.mainResponsiblePerson = mainResponsiblePerson;
  }
  
  public String getMainResponsiblePerson() {
    return mainResponsiblePerson;
  }
  
  public void setVersionCategory(String versionCategory) {
    this.versionCategory = versionCategory;
  }
  
  public String getVersionCategory() {
    return versionCategory;
  }
  
  public void setYears(String years) {
    this.years = years;
  }
  
  public String getYears() {
    return years;
  }
  
  public void setReviewerOfPostscript(String reviewerOfPostscript) {
    this.reviewerOfPostscript = reviewerOfPostscript;
  }
  
  public String getReviewerOfPostscript() {
    return reviewerOfPostscript;
  }
  
  public void setTibetanSeal(String tibetanSeal) {
    this.tibetanSeal = tibetanSeal;
  }
  
  public String getTibetanSeal() {
    return tibetanSeal;
  }
  
  public void setPlateFrame(String plateFrame) {
    this.plateFrame = plateFrame;
  }
  
  public String getPlateFrame() {
    return plateFrame;
  }
  
  public void setBindingStatus(String bindingStatus) {
    this.bindingStatus = bindingStatus;
  }
  
  public String getBindingStatus() {
    return bindingStatus;
  }
  
  public void setCollectionOrganization(String collectionOrganization) {
    this.collectionOrganization = collectionOrganization;
  }
  
  public String getCollectionOrganization() {
    return collectionOrganization;
  }
  
  public void setVolumeName(String volumeName) {
    this.volumeName = volumeName;
  }
  
  public String getVolumeName() {
    return volumeName;
  }
  
  public void setInventoryVolume(String inventoryVolume) {
    this.inventoryVolume = inventoryVolume;
  }
  
  public String getInventoryVolume() {
    return inventoryVolume;
  }
  
  public void setFolio(String folio) {
    this.folio = folio;
  }
  
  public String getFolio() {
    return folio;
  }
  
  public void setIndexingPreparer(String indexingPreparer) {
    this.indexingPreparer = indexingPreparer;
  }
  
  public String getIndexingPreparer() {
    return indexingPreparer;
  }
  
  public void setCallNumber(String callNumber) {
    this.callNumber = callNumber;
  }
  
  public String getCallNumber() {
    return callNumber;
  }
  
  public void setProcessingRecordNo(String processingRecordNo) {
    this.processingRecordNo = processingRecordNo;
  }
  
  public String getProcessingRecordNo() {
    return processingRecordNo;
  }
  
  public void setCensusNo(String censusNo) {
    this.censusNo = censusNo;
  }
  
  public String getCensusNo() {
    return censusNo;
  }
  
  public void setClassification(String classification) {
    this.classification = classification;
  }
  
  public String getClassification() {
    return classification;
  }
  
  public void setSubcategory(String subcategory) {
    this.subcategory = subcategory;
  }
  
  public String getSubcategory() {
    return subcategory;
  }
  
  public void setMetadataTemplate(String metadataTemplate) {
    this.metadataTemplate = metadataTemplate;
  }
  
  public String getMetadataTemplate() {
    return metadataTemplate;
  }
  
  public void setMasterSlave(String masterSlave) {
    this.masterSlave = masterSlave;
  }
  
  public String getMasterSlave() {
    return masterSlave;
  }
  
  public void setMasterBookId(String masterBookId) {
    this.masterBookId = masterBookId;
  }
  
  public String getMasterBookId() {
    return masterBookId;
  }
  
  public void setNumberOfCopies(String numberOfCopies) {
    this.numberOfCopies = numberOfCopies;
  }
  
  public String getNumberOfCopies() {
    return numberOfCopies;
  }
  
  public void setVolumeNo(String volumeNo) {
    this.volumeNo = volumeNo;
  }
  
  public String getVolumeNo() {
    return volumeNo;
  }
  
  public void setPicturePath(String picturePath) {
    this.picturePath = picturePath;
  }
  
  public String getPicturePath() {
    return picturePath;
  }
  
  public void setMonographicLiterature(String monographicLiterature) {
    this.monographicLiterature = monographicLiterature;
  }
  
  public String getMonographicLiterature() {
    return monographicLiterature;
  }
  
  public void setDocumentType(String documentType) {
    this.documentType = documentType;
  }
  
  public String getDocumentType() {
    return documentType;
  }
  
  public void setDescs(String descs) {
    this.descs = descs;
  }
  
  public String getDescs() {
    return descs;
  }
  
  public void setIp(String ip) {
    this.ip = ip;
  }
  
  public String getIp() {
    return ip;
  }
  
  public void setCreaterId(Long createrId) {
    this.createrId = createrId;
  }
  
  public Date getCreateTime() {
    return createTime;
  }
  
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
  
  public Long getCreaterId() {
    return createrId;
  }
  
  public void setWhetherDelete(Integer whetherDelete) {
    this.whetherDelete = whetherDelete;
  }
  
  public Integer getWhetherDelete() {
    return whetherDelete;
  }
  
  public String getCode() {
    return code;
  }
  
  public void setCode(String code) {
    this.code = code;
  }
  
  public Integer getIndex() {
    return index;
  }
  
  public void setIndex(Integer index) {
    this.index = index;
  }
}
