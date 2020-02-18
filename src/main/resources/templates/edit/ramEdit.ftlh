<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "RAM editing">
    <h2>${errorMessage!}</h2>

    <h1>Зміна оперативної пам'яті:</h1>
    <form method="post" action="/ram/edit/${editRam.id}">
        <label>Модель: <input type="text" name="model" value="${editRam.model!}"/></label>
        <label>Обсяг пам'яті: <input type="number" name="memory" min="1" value="${editRam.memory!}"/></label>
        <button type="submit">Зберегти</button>
    </form>
</@pad.page>