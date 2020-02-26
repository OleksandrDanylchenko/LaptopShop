<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "Hardware editing">
    <@error.errorOutput errorMessage!/>

    <p class="h1">Змінити ноутбук:</p>
    <form method="post" action="/laptop/edit/${editLaptop.id}">
        <#if labelModels?has_content>
            <div class="form-row my-2">
                <select class="custom-select" name="labelModel">
                    <option value="" selected disabled>Оберіть модель</option>
                    <#list labelModels as labelModel>
                        <#if editLaptop.label??>
                            <option value="${labelModel}"<#if labelModel == editLaptop.label.model>
                                selected="selected"</#if>>${labelModel}</option>
                        <#else>
                            <option>${labelModel}</option>
                        </#if>
                    </#list>
                </select>
            </div>
        </#if>

        <#if typeNames?has_content>
            <div class="form-row my-2">
                <select class="custom-select" name="typeName">
                    <option value="" selected disabled>Оберіть тип</option>
                    <#list typeNames as typeName>
                        <#if editLaptop.type??>
                            <option value="${typeName}"<#if typeName == editLaptop.type.name>
                                selected="selected"</#if>>${typeName}</option>
                        <#else>
                            <option>${typeName}</option>
                        </#if>
                    </#list>
                </select>
            </div>
        </#if>

        <#if typeNames?has_content>
            <div class="form-row my-2">
                <select class="custom-select" name="hardwareAssemblyName">
                    <option value="" selected disabled>Оберіть збірку</option>
                    <#list hardwareAssemblyNames as hardwareAssemblyName>
                        <#if editLaptop.hardware??>
                            <option value="${hardwareAssemblyName}"<#if hardwareAssemblyName == editLaptop.hardware.assemblyName>
                                selected="selected"</#if>>${hardwareAssemblyName}</option>
                        <#else>
                            <option>${hardwareAssemblyName}</option>
                        </#if>
                    </#list>
                </select>
            </div>
        </#if>
        <button type="submit" class="btn btn-primary mt-1 float-right">Зберегти</button>
    </form>
</@pad.page>