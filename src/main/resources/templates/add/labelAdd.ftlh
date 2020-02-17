<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/mainRef.ftlh" as main>

<@pad.page "Label adding">
    <h2>${errorMessage!}</h2>

    <div>
        <h1>Додати нове найменування:</h1>
        <form method="post" action="/label/add">
            <label>Новий бренд: <input type="text" name="brand" value="${brand!}"/></label>
            <label>Нова модель: <input type="text" name="model" value="${model!}"/></label>
            <button type="submit">Додати</button>
        </form>
    </div>
</@pad.page>