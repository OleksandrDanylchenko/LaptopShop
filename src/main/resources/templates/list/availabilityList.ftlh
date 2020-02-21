<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/mainRef.ftlh" as main>
<#setting locale="uk">

<@pad.page "Availability">
    <@main.mainPageRef/>

    <div>
        <p><a href="/availability/add">Додати новий запис про наявність</a></p>
    </div>

    <div>
        <p>Знайти запис про наявність за критеріями:</p>
        <form method="get" action="/availability">
            <label>Модель ноутбука: <input type="text" name="laptopModel" value="${laptopModel!}"/></label>
            <label>Ціна: <input type="number" step="10" min="5000" name="price" value="${(price??)?then(price?c, '')}"/></label>
            <label>Кількість: <input type="number" step="1" min="1" name="quantity" value="${(quantity??)?then(quantity?c, '')}"/></label>
            <label>Адреса магазину: <input type="text" name="shopAddress" value="${shopAddress!}"/></label>
            <p><label>Дата початку продаж: <input type="date" name="dateStartStr" value="${dateStartStr!}" min="2019-02-16"></label>
            <label>Дата закінчення продаж: <input type="date" name="dateEndStr" value="${dateEndStr!}" min="2019-02-16"></label></p>
            <button type="submit">Шукати</button>
        </form>
    </div>

    <#if availabilities?has_content>
        <div><p>Список записів про наявність:</p></div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Модель</th>
                <th>Ціна</th>
                <th>Кількість</th>
                <th>Номер магазину</th>
                <th>Адреса магазину</th>
                <th>Початок продаж</th>
                <th>Закінчення продаж</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list availabilities as availability>
                <tr>
                    <td>${availability.id}</td>
                    <td>${availability.laptop.label.model}</td>
                    <td>${availability.price}</td>
                    <td>${availability.quantity}</td>
                    <td>${availability.shop.id}</td>
                    <td>${availability.shop.address}</td>
                    <td>${availability.dateStart}</td>
                    <td>${availability.dateEnd}</td>
                    <td><a href="/availability/edit/${availability.id}">Edit</a></td>
                    <td><a href="/availability/delete/${availability.id}" onclick="return confirm('Are you sure?')">Delete</a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <h3>Не знайдено жодного запису про наявність.</h3>
    </#if>
</@pad.page>