<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "Laptop adding">
    <#if hardwareAssemblyNames?has_content>
        <#if typeNames?has_content>
            <#if labelModels?has_content>
                <@error.errorOutput errorMessage!/>

                <p class="h1">Додати новий ноутбук:</p>
                <form method="post" action="/laptop/add" class="form-group">
                    <div class="form-row my-2">
                        <select class="custom-select" name="labelModel">
                            <option value="" selected disabled>Оберіть нову модель</option>
                            <#list labelModels as labelModel>
                                <option>${labelModel}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="form-row my-2">
                        <select class="custom-select" name="typeName">
                            <option value="" selected disabled>Оберіть тип</option>
                            <#list typeNames as typeName>
                                <option>${typeName}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="form-row my-2">
                        <select class="custom-select" name="hardwareAssemblyName">
                            <option value="" selected disabled>Оберіть збірку</option>
                            <#list hardwareAssemblyNames as hardwareAssemblyName>
                                <option>${hardwareAssemblyName}</option>
                            </#list>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary mt-1 float-right">Додати</button>
                </form>
            <#else>
                <div class="alert alert-warning" role="alert">
                    <div class="h1 text-center">Не знайдено жодного найменування.</div>
                    <div class="h3 text-center"><a href="/label/add">Додайте нове найменування</a> для створення ноутбуку.</div>
                </div>
            </#if>
        <#else>
            <div class="alert alert-warning" role="alert">
                <div class="h1 text-center">>Не знайдено жодного типу.</div>
                <div class="h3 text-center"><a href="/type/add">Додайте новий тип</a> для створення ноутбуку.</div>
            </div>
        </#if>
    <#else>
        <div class="alert alert-warning" role="alert">
            <div class="h1 text-center">>Не знайдено жодної збірки.</div>
            <div class="h3 text-center"><a href="/hardware/add">Додайте нову збірку</a> для створення ноутбуку.</div>
        </div>
    </#if>
</@pad.page>