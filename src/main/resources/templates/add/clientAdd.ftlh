<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Client padding">
    <h2>${errorMessage!}</h2>

    <div>
        <h1>Додати нового клієнта:</h1>
        <form method="post" action="/client/add">
            <label>Ім'я нового клієнта: <input type="text" name="firstName"/></label>
            <label>Прізвище нового клієнта: <input type="text" name="secondName"/></label>
            <label>Дата реєстрації: <input type="date" name="dateRegStr" min="2019-02-16"></label>
            <button type="submit">Додати</button>
        </form>
    </div>
</@pad.page>