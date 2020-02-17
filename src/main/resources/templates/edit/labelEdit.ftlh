<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/mainRef.ftlh" as main>

<@pad.page "Label edit">
    <h2>${errorMessage!}</h2>

    <h1>Зміна найменування:</h1>
    <form method="post" action="/label/edit/${editLabel.id}">
        <label>Бренд: <input type="text" name="brand" value="${editLabel.brand!}"/></label>
        <label>Модель: <input type="text" name="model" value="${editLabel.model!}"/></label>
        <button type="submit">Зберегти</button>
    </form>
</@pad.page>