<#-- このテンプレートに対応するデータモデルのクラスは org.seasar.doma.extension.gen.EntityDesc です -->

<#-- 型変換をする関数を定義 -->
<#function convertDataType dataType>
    <#local result = dataType?replace("Byte", "Integer")>
    <#local result = result?replace("int", "Integer")>
    <#local result = result?replace("Timestamp", "LocalDateTime")>
    <#return result>
</#function>

<#-- Timestampを変換する関数 -->
<#function convertImportClass class>
    <#local result = class?replace("java.sql.Timestamp", "java.time.LocalDateTime")>
    <#return result>
</#function>

<#import "/columnNameLib.ftl" as columnNamelib>
<#-- カラム名を変換する関数 -->
<#function convertColumnName columnName name>
    <#local result = columnNamelib.columnName[columnName]!name>
    <#return result>
</#function>

<#import "/lib.ftl" as lib>
<#if lib.copyright??>
${lib.copyright}
</#if>
<#if packageName??>
package ${packageName};
</#if>

<#list importNames as importName>
<#-- namingTypeがNONEじゃない場合はColumnをインポートしない -->
<#if namingType != "NONE" && importName == "org.seasar.doma.Column">
<#else>
import ${convertImportClass(importName)};
</#if>
</#list>

/**
<#if showDbComment && comment??>
 * ${comment}エンティティ
</#if>
 *
<#if lib.author??>
 * @author ${lib.author}
</#if>
 */
<#if !useAccessor>
@lombok.Data
</#if>
@Entity<#if useListener || namingType != "NONE">(</#if><#if useListener>listener = ${listenerClassSimpleName}.class</#if><#if namingType != "NONE"><#if useListener>, </#if>naming = ${namingType.referenceName}</#if><#if useListener || namingType != "NONE">)</#if>
<#if showCatalogName && catalogName?? || showSchemaName && schemaName?? || showTableName && tableName??>
@Table(<#if showCatalogName && catalogName??>catalog = "${catalogName}"</#if><#if showSchemaName && schemaName??><#if showCatalogName && catalogName??>, </#if>schema = "${schemaName}"</#if><#if showTableName><#if showCatalogName && catalogName?? || showSchemaName && schemaName??>, </#if>name = "${tableName}"</#if>)
</#if>
public class ${simpleName}<#if superclassSimpleName??> extends ${superclassSimpleName}</#if> {
<#list ownEntityPropertyDescs as property>

  <#if showDbComment && property.comment??>
    /** ${property.comment} */
  <#else>
    /** */
  </#if>
  <#if property.id>
    @Id
    <#if property.generationType??>
    @GeneratedValue(strategy = ${property.generationType.referenceName})
      <#if property.generationType == "SEQUENCE">
    @SequenceGenerator(sequence = "${tableName}_${property.columnName}"<#if property.initialValue??>, initialValue = ${property.initialValue}</#if><#if property.allocationSize??>, allocationSize = ${property.allocationSize}</#if>)
      <#elseif property.generationType == "TABLE">
    @TableGenerator(pkColumnValue = "${tableName}_${property.columnName}"<#if property.initialValue??>, initialValue = ${property.initialValue}</#if><#if property.allocationSize??>, allocationSize = ${property.allocationSize}</#if>)
      </#if>
    </#if>
  </#if>
  <#if property.version>
    @Version
  </#if>
  <#-- namingTypeがNONEじゃない場合はColumnをアノテートしない -->
  <#if namingType == "NONE" && property.showColumnName && property.columnName??>
    @Column(name = "${property.columnName}")
  </#if>
    <#if namingType == "NONE">private<#else>protected</#if> ${convertDataType(property.propertyClassSimpleName)} ${convertColumnName(property.columnName, property.name)};
</#list>
<#if originalStatesPropertyName??>

    /** */
    @OriginalStates
    ${simpleName} ${originalStatesPropertyName};
</#if>
<#if useAccessor>
  <#list ownEntityPropertyDescs as property>

    /** 
     * ${property.name}を取得する
     * 
     * @return ${property.name}
     */
    public ${convertDataType(property.propertyClassSimpleName)} get${convertColumnName(property.columnName, property.name)?cap_first}() {
        return ${convertColumnName(property.columnName, property.name)};
    }

    /** 
     * ${property.name}を設定する
     * 
     * @param ${convertColumnName(property.columnName, property.name)} ${property.name}
     */
    public void set${convertColumnName(property.columnName, property.name)?cap_first}(${convertDataType(property.propertyClassSimpleName)} ${convertColumnName(property.columnName, property.name)}) {
        this.${convertColumnName(property.columnName, property.name)} = ${convertColumnName(property.columnName, property.name)};
    }
  </#list>
</#if>
}