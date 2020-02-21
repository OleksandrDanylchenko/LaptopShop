<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Availability editing">
    <h2>${errorMessage!}</h2>

    <h1>Змінити запис про наявність:</h1>
    <form method="post" action="/availability/edit/${editAvailability.id}">
        <label>Модель ноутбука:
            <select name="laptopModel">
                <#list laptopModels as model>
                    <option>${model}</option>
                </#list>
            </select>
        </label>
        <label>Ціна: <input type="number" step="10" min="5000" name="price" value="${(editAvailability.price??)?then(editAvailability.price?c, '')}"/></label>
        <label>Кількість: <input type="number" step="1" min="1" name="quantity" value="${(editAvailability.quantity??)?then(editAvailability.quantity?c, '')}"/></label>
        <label>Адреса магазину:
            <select name="shopAddress">
                <#list shopAddresses as address>
                    <option>${address}</option>
                </#list>
            </select>
        </label>
        <p><label>Дата початку продаж: <input type="date" name="dateStart" min="2019-02-16" value="${editAvailability.dateStart?iso_utc}"></label>
            <label>Дата закінчення продаж: <input type="date" name="dateEnd"  min="2019-02-16" value="${editAvailability.dateEnd?iso_utc}"></label></p>
        <button type="submit">Додати</button>
    </form>
</@pad.page>