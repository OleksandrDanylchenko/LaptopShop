<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "CPU adding">
    <h2>${errorMessage!}</h2>

    <h1>Додати новий процесор:</h1>
    <form method="post" action="/cpu/add">
        <label>Нова модель: <input type="text" name="model" value="${RequestParameters.model!}"/></label>
        <label>Нова частота: <input type="number" step="0.1" name="frequency" min="0.1" max="5" value="${RequestParameters.frequency!}"/></label>
        <button type="submit">Додати</button>
    </form>
</@pad.page>