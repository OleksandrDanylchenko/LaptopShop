<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Display editing">
    <h2>${errorMessage!}</h2>

    <h1>Зміна інформації дисплея:</h1>
    <form method="post" action="/display/edit/${editDisplay.id}">
        <label>Тип: <input type="text" name="type" value="${editDisplay.type!}"/></label>
        <label>Діагональ: <input type="number" step="0.1" name="diagonal" value="${editDisplay.diagonal!}"/></label>
        <label>Розширення: <input type="text" name="resolution" value="${editDisplay.resolution!}"/></label>
        <button type="submit">Зберегти</button>
    </form>
</@pad.page>