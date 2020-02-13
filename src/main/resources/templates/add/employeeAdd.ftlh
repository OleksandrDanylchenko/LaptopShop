<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Employee adding">
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
</@pad.page>