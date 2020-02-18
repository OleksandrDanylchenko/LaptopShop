<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "RAM adding">
    <h2>${errorMessage!}</h2>

    <h1>Додати нову оперативну пам'ять:</h1>
    <form method="post" action="/ram/add">
        <label>Нова модель: <input type="text" name="model" value="${model!}"/></label>
        <label>Новий обсяг пам'яті: <input type="number" name="memory" min="1" value="${memory!}"/></label>
        <button type="submit">Додати</button>
    </form>
</@pad.page>