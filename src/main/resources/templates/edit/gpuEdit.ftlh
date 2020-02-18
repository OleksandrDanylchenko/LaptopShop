<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "GPU editing">
    <h2>${errorMessage!}</h2>

    <h1>Зміна відеокарти:</h1>
    <form method="post" action="/gpu/edit/${editGpu.id}">
        <label>Модель: <input type="text" name="model" value="${editGpu.model!}"/></label>
        <label>Обсяг пам'яті: <input type="number" name="memory" min="1" max="64" value="${editGpu.memory!}"/></label>
        <button type="submit">Зберегти</button>
    </form>
</@pad.page>