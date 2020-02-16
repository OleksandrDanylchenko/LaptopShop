<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Employee adding">
    <#if shopAddresses?has_content>
        <div>
            <h1>Додати нового співробітника:</h1>
            <form method="post" action="/employee/add">
                <label>Ім'я нового співробітника: <input type="text" name="firstName"/></label>
                <label>Прізвище нового співробітника: <input type="text" name="secondName"/></label>
                <label>Адреса магазину:
                    <select name="shopAddress">
                        <#list shopAddresses as address>
                            <option>${address}</option>
                        </#list>
                    </select>
                </label>
                <button type="submit">Додати</button>
            </form>
        </div>
    <#else>
        <h1>Не знайдено жодного активного магазину.</h1>
        <p><a href="/shop/add">Додайте новий магазин</a> для перегляду та редагування штату співробітників.</p>
    </#if>
</@pad.page>