<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Display adding">
    <h2>${errorMessage!}</h2>

    <div>
        <h1>Додати новий дисплей:</h1>
        <form method="post" action="/display/add">
            <label>Новий тип: <input type="text" name="type" value="${type!}"/></label>
            <label>Нова діагональ: <input type="number" step="0.1" name="diagonal" value="${diagonal!}"/></label>
            <label>Нове розширення: <input type="text" name="resolution" value="${resolution!}"/></label>
            <button type="submit">Додати</button>
        </form>
    </div>
</@pad.page>