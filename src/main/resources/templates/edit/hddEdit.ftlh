<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "HDD editing">
    <h2>${errorMessage!}</h2>

    <h1>Зміна HDD диску:</h1>
    <form method="post" action="/hdd/edit/${editHDD.id}">
        <label>Модель: <input type="text" name="model" value="${editHDD.model!}"/></label>
        <label>Обсяг пам'яті: <input type="number" name="memory" min="1" value="${editHDD.memory!?c}"/></label>
        <button type="submit">Зберегти</button>
    </form>
</@pad.page>