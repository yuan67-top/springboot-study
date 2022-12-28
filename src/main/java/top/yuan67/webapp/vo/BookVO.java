package top.yuan67.webapp.vo;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.StringJoiner;

/**
 * @Author: NieQiang
 * @User: CAPTAIN
 * @CreateTime: 2022-12-29
 * @Desc: 描述信息
 **/
public class BookVO {
  @Field(index = false, type = FieldType.Keyword)
  private String code; // 编号
  private String title; // 题名
  private String mainResponsiblePerson; // 主要责任者
  private String years; // 年代
  private String collectionOrganization; // 馆藏机构
  private String volumeName; // 卷名（卷次）
  private String inventoryVolume; // 存缺卷
  private String callNumber; // 索书号
  private String classification; // 分类
  private String subcategory; // 子分类
  private String masterSlave; // 主从
  private String monographicLiterature; // 专题文献
  private String documentType; // 文献类型
  
  protected BookVO() {
  }
  
  public BookVO(String name) {
    this.code = name;
    this.title = name;
    this.mainResponsiblePerson = name;
    this.years = name;
    this.collectionOrganization = name;
    this.volumeName = name;
    this.inventoryVolume = name;
    this.callNumber = name;
    this.classification = name;
    this.subcategory = name;
    this.masterSlave = name;
    this.monographicLiterature = name;
    this.documentType = name;
  }
  
  @Override
  public String toString() {
    return new StringJoiner(", ", BookVO.class.getSimpleName() + "[", "]")
        .add("code='" + code + "'")
        .add("title='" + title + "'")
        .add("mainResponsiblePerson='" + mainResponsiblePerson + "'")
        .add("years='" + years + "'")
        .add("collectionOrganization='" + collectionOrganization + "'")
        .add("volumeName='" + volumeName + "'")
        .add("inventoryVolume='" + inventoryVolume + "'")
        .add("callNumber='" + callNumber + "'")
        .add("classification='" + classification + "'")
        .add("subcategory='" + subcategory + "'")
        .add("masterSlave='" + masterSlave + "'")
        .add("monographicLiterature='" + monographicLiterature + "'")
        .add("documentType='" + documentType + "'")
        .toString();
  }
  
  public String getCode() {
    return code;
  }
  
  public void setCode(String code) {
    this.code = code.trim() == "" ? "" :code;
  }
  
  public String getTitle() {
    return title;
  }
  
  public void setTitle(String title) {
    this.title = title.trim() == "" ? "" : title;
  }
  
  public String getMain_responsible_person() {
    return mainResponsiblePerson;
  }
  
  public void setMain_responsible_person(String mainResponsiblePerson) {
    this.mainResponsiblePerson = mainResponsiblePerson.trim() == "" ? "" : mainResponsiblePerson;
  }
  
  public String getYears() {
    return years;
  }
  
  public void setYears(String years) {
    this.years = years.trim() == "" ? "" : years;
  }
  
  public String getCollection_organization() {
    return collectionOrganization;
  }
  
  public void setCollection_organization(String collectionOrganization) {
    this.collectionOrganization = collectionOrganization.trim() == "" ? "" : collectionOrganization;
  }
  
  public String getVolume_name() {
    return volumeName;
  }
  
  public void setVolume_name(String volumeName) {
    this.volumeName = volumeName.trim() == "" ? "" :volumeName;
  }
  
  public String getInventory_volume() {
    return inventoryVolume;
  }
  
  public void setInventory_volume(String inventoryVolume) {
    this.inventoryVolume = inventoryVolume.trim() == "" ? "" :inventoryVolume;
  }
  
  public String getCall_number() {
    return callNumber;
  }
  
  public void setCall_number(String callNumber) {
    this.callNumber = callNumber.trim() == "" ? "" : callNumber;
  }
  
  public String getClassification() {
    return classification;
  }
  
  public void setClassification(String classification) {
    this.classification = classification.trim() == "" ? "" : classification;
  }
  
  public String getSubcategory() {
    return subcategory;
  }
  
  public void setSubcategory(String subcategory) {
    this.subcategory = subcategory.trim() == "" ? "" : subcategory;
  }
  
  public String getMaster_slave() {
    return masterSlave;
  }
  
  public void setMaster_slave(String masterSlave) {
    this.masterSlave = masterSlave.trim() == "" ? "" : masterSlave;
  }
  
  public String getMonographic_literature() {
    return monographicLiterature;
  }
  
  public void setMonographic_literature(String monographicLiterature) {
    this.monographicLiterature = monographicLiterature.trim() == "" ? "" : monographicLiterature;
  }
  
  public String getDocument_type() {
    return documentType;
  }
  
  public void setDocument_type(String documentType) {
    this.documentType = documentType.trim() == "" ? "" : documentType;
  }
}
