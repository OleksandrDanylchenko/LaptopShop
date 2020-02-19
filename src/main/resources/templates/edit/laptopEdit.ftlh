<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Hardware editing">
    <h2>${errorMessage!}</h2>

    <h1>Змінити ноутбук:</h1>
    <form method="post" action="/laptop/edit/${editLaptop.id}">
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
        <button type="submit">Зберегти</button>
    </form>
</@pad.page>