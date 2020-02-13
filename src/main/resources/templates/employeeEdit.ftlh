<#import "parts/htmlPad.ftlh" as pad>

<@pad.page "Employee editing">
    <h1>Зміна інформації співробітника:</h1>
    <form method="post" action="/employee/edit/${editShop.id}">
        <label>Ім'я:   <input type="text" name="address" value="${editShop.address}"></label>
        <label>Прізвище: <input type="text" name="secondName"/></label>
        <input type="hidden" value="${editShop.id}" name="id">
        <button type="submit">Зберегти</button>
    </form>
</@pad.page>