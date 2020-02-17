<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Client edit">
    <h2>${errorMessage!}</h2>

    <h1>Зміна інформації співробітника:</h1>
    <form method="post" action="/client/edit/${editClient.id}">
        <label>Ім'я:   <input type="text" name="firstName" value="${editClient.firstName}"></label>
        <label>Прізвище:   <input type="text" name="secondName" value="${editClient.secondName}"></label>
        <label>Дата реєстрації:   <input type="date" name="dateRegStr" min="2019-02-16" value="${dateRegStr}"></label>
        <input type="hidden" value="${editClient.id}" name="id">
        <button type="submit">Зберегти</button>
    </form>
</@pad.page>