<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "GPU adding">
    <h2>${errorMessage!}</h2>

    <h1>Додати нову відеокарту:</h1>
    <form method="post" action="/gpu/add">
        <label>Нова модель: <input type="text" name="model" value="${model!}"/></label>
        <label>Новий обсяг пам'яті: <input type="number" name="memory" min="1" max="64" value="${memory!}"/></label>
        <button type="submit">Додати</button>
    </form>
</@pad.page>