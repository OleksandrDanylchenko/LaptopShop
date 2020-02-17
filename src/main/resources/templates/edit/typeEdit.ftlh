<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Type editing">
    <h2>${errorMessage!}</h2>

    <h1>Зміна назви типу:</h1>
    <form method="post" action="/type/edit/${editType.id}">
        <label>Назва типу:   <input type="text" name="name" value="${editType.name!}"></label>
        <button type="submit">Зберегти</button>
    </form>
</@pad.page>