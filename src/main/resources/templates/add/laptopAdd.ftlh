<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Laptop adding">
    <#if hardwareAssemblyNames?has_content>
        <#if typeNames?has_content>
            <#if labelModels?has_content>
                <h2>${errorMessage!}</h2>

                <div>
                    <h1>Додати новий ноутбук:</h1>
                    <form method="post" action="/laptop/add">
                        <label>Модель нового ноутбуку:
                            <select name="labelModel">
                                <#list labelModels as labelModel>
                                    <option>${labelModel}</option>
                                </#list>
                            </select>
                        </label>
                        <label>Тип нового ноутбуку:
                            <select name="typeName">
                                <#list typeNames as typeName>
                                    <option>${typeName}</option>
                                </#list>
                            </select>
                        </label>
                        <label>Збірка нового ноутбуку:
                            <select name="hardwareAssemblyName">
                                <#list hardwareAssemblyNames as hardwareAssemblyName>
                                    <option>${hardwareAssemblyName}</option>
                                </#list>
                            </select>
                        </label>
                        <button type="submit">Додати</button>
                    </form>
                </div>
            <#else>
                <h1>Не знайдено жодного найменування.</h1>
                <p><a href="/label/add">Додайте нове найменування</a> для створення ноутбуку.</p>
            </#if>
        <#else>
            <h1>Не знайдено жодного типу.</h1>
            <p><a href="/type/add">Додайте новий тип</a> для створення ноутбуку.</p>
        </#if>
    <#else>
        <h1>Не знайдено жодної збірки.</h1>
        <p><a href="/hardware/add">Додайте нову збірку</a> для створення ноутбуку.</p>
    </#if>
</@pad.page>