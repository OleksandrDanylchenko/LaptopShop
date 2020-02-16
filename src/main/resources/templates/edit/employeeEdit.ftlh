<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Employee editing">
    <h2>${errorMessage!}</h2>

    <h1>Зміна інформації співробітника:</h1>
    <form method="post" action="/employee/edit/${editEmployee.id}">
        <label>Ім'я:   <input type="text" name="firstName" value="${editEmployee.firstName}"></label>
        <label>Прізвище: <input type="text" name="secondName" value="${editEmployee.secondName}"></label>
        <label>Адреса магазину:
            <select name="shopAddress">
                <#list shopAddresses as address>
                    <option>${address}</option>
                </#list>
            </select>
        </label>
        <input type="hidden" value="${editEmployee.id}" name="id">
        <button type="submit">Зберегти</button>
    </form>
</@pad.page>