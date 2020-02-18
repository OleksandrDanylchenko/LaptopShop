<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "SSD editing">
    <h2>${errorMessage!}</h2>

    <h1>Зміна SSD диску:</h1>
    <form method="post" action="/ssd/edit/${editSSD.id}">
        <label>Модель: <input type="text" name="model" value="${editSSD.model!}"/></label>
        <label>Обсяг пам'яті: <input type="number" name="memory" min="1" value="${(editSSD.memory??)?then(editSSD.memory?c, '')}"/></label>
        <button type="submit">Зберегти</button>
    </form>
</@pad.page>