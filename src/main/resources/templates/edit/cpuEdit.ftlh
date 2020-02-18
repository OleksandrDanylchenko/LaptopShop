<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "CPU editing">
    <h2>${errorMessage!}</h2>

    <h1>Зміна процесора:</h1>
    <form method="post" action="/cpu/edit/${editCpu.id}">
        <label>Модель: <input type="text" name="model" value="${editCpu.model!}"/></label>
        <label>Частота: <input type="number" step="0.1" name="frequency" min="0.1" max="5" value="${editCpu.frequency!}"/></label>
        <button type="submit">Зберегти</button>
    </form>
</@pad.page>