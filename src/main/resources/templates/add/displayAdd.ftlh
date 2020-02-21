<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Display adding">
    <h2>${errorMessage!}</h2>

    <div>
        <h1>Додати новий дисплей:</h1>
        <form method="post" action="/display/add">
            <label>Нова модель: <input type="text" name="model" value="${RequestParameters.model!}"/></label>
            <label>Новий тип: <input type="text" name="type" value="${RequestParameters.type!}"/></label>
            <label>Нова діагональ: <input type="number" step="0.1" name="diagonal" min="0.1" value="${RequestParameters.diagonal!}"/></label>
            <label>Нове розширення: <input type="text" name="resolution" value="${RequestParameters.resolution!}"/></label>
            <button type="submit">Додати</button>
        </form>
    </div>
</@pad.page>