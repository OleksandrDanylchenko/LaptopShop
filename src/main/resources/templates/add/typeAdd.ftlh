<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Type adding">
    <h2>${errorMessage!}</h2>

    <div>
        <h1>Додати новий тип:</h1>
        <form method="post" action="/type/add">
            <label>Назва нового типу: <input type="text" name="name" value="${RequestParameters.name!}"/></label>
            <button type="submit">Додати</button>
        </form>
    </div>
</@pad.page>