<#import "../parts/htmlPad.ftlh" as pad>


<@pad.page "Laptop">
    <div>
        <p><a href="/laptop/add">Додати новий ноутбук</a></p>
    </div>

    <div>
        <p>Знайти ноутбук за критеріями:</p>
        <form method="get" action="/laptop">
            <label>Бренд: <input type="text" name="labelBrand" value="${RequestParameters.labelBrand!}"/></label>
            <label>Модель: <input type="text" name="labelModel" value="${RequestParameters.labelModel!}"/></label>
            <label>Тип: <input type="text" name="typeName" value="${RequestParameters.typeName!}"/></label>
            <label>Назва збірки: <input type="text" name="hardwareAssemblyName"
                                        value="${RequestParameters.hardwareAssemblyName!}"/></label>
            <button type="submit">Шукати</button>
        </form>
    </div>

    <#if laptops?has_content>
        <div><p>Список ноутбуків:</p></div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Бренд</th>
                <th>Модель</th>
                <th>Тип</th>
                <th>Збірка</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list laptops as laptop>
                <tr>
                    <td>${laptop.id}</td>
                    <td>${laptop.label.brand}</td>
                    <td>${laptop.label.model}</td>
                    <td><#if laptop.type??>${laptop.type.name}<#else>Видалено</#if></td>
                    <td><#if laptop.hardware??>${laptop.hardware.assemblyName}<#else>Видалено</#if></td>
                    <td><a href="/laptop/edit/${laptop.id}">Edit</a></td>
                    <td><a href="/laptop/delete/${laptop.id}" onclick="return confirm('Ви впевнені?')">Delete</a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <h3>Не знайдено жодного ноутбуку.</h3>
    </#if>
</@pad.page>