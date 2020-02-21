<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Client editing">
    <h2>${errorMessage!}</h2>

    <h1>Зміна інформації співробітника:</h1>
    <form method="post" action="/client/edit/${editClient.id}">
        <label>Ім'я:   <input type="text" name="firstName" value="${editClient.firstName}"></label>
        <label>Прізвище:   <input type="text" name="secondName" value="${editClient.secondName}"></label>
        <label>Дата реєстрації:   <input type="date" name="dateReg" min="2019-02-16" value="${editClient.dateReg?iso_utc}"></label>
        <button type="submit">Зберегти</button>
    </form>
</@pad.page>