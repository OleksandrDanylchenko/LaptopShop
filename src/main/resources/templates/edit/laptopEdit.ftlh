<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Hardware editing">
    <h2>${errorMessage!}</h2>

    <h1>Змінити ноутбук:</h1>
    <form method="post" action="/laptop/edit/${editLaptop.id}">
        <#if labelModels?has_content>
            <label>Модель нового ноутбуку:
                <select name="labelModel">
                    <#list labelModels as labelModel>
                        <#if editLaptop.laptop??>
                            <option value="${labelModel}"<#if labelModel == editLaptop.laptop.model>
                                selected="selected"</#if>>${labelModel}</option>
                        <#else>
                            <option>${labelModel}</option>
                        </#if>
                    </#list>
                </select>
            </label>
        </#if>

        <label>Тип нового ноутбуку:
            <select name="typeName">
                <#list typeNames as typeName>
                    <option>${typeName}</option>
                </#list>
            </select>
        </label>

        <#if typeNames?has_content>
            <label>Тип нового ноутбуку:
                <select name="typeName">
                    <#list typeNames as typeName>
                        <#if editLaptop.type??>
                            <option value="${typeName}"<#if typeName == editLaptop.type.name>
                                selected="selected"</#if>>${typeName}</option>
                        <#else>
                            <option>${typeName}</option>
                        </#if>
                    </#list>
                </select>
            </label>
        </#if>

        <#if typeNames?has_content>
            <label>Збірка нового ноутбуку:
                <select name="hardwareAssemblyName">
                    <#list hardwareAssemblyNames as hardwareAssemblyName>
                        <#if editLaptop.hardware??>
                            <option value="${hardwareAssemblyName}"<#if hardwareAssemblyName == editLaptop.hardware.name>
                                selected="selected"</#if>>${hardwareAssemblyName}</option>
                        <#else>
                            <option>${hardwareAssemblyName}</option>
                        </#if>
                    </#list>
                </select>
            </label>
        </#if>
        <button type="submit">Зберегти</button>
    </form>
</@pad.page>