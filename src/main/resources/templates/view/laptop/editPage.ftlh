<#import "../../parts/htmlPad.ftlh" as htmlPad>
<#import "../../errorsHandlers/errorOutput.ftlh" as eO>

<@htmlPad.page "Laptop editing">
    <div class="container">
        <@eO.errorOutput errorMessage!/>

        <p class="h1">Змінити ноутбук:</p>
        <form method="post" action="/laptop/edit/${editLaptop.id}" class="form-group">
            <#if labelModels?has_content>
                <div class="form-row my-2">
                    <div class="col">
                        <label class="d-inline ml-2" for="laptopModel">Назва моделі:</label>
                        <select class="custom-select" name="labelModel">
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
                </div>
            </#if>

            <#if typeNames?has_content>
                <div class="form-row my-2">
                    <div class="col">
                        <label class="d-inline ml-2" for="typeName">Назва типу:</label>
                        <select class="custom-select" name="typeName">
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
                </div>
            </#if>

            <#if typeNames?has_content>
                <div class="form-row my-2">
                    <div class="col">
                        <label class="d-inline ml-2" for="hardwareAssemblyName">Назва збірки:</label>
                        <select class="custom-select" name="hardwareAssemblyName">
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
                </div>
            </#if>
            <button type="submit" class="btn btn-danger float-right">Змінити</button>
        </form>
    </div>
</@htmlPad.page>