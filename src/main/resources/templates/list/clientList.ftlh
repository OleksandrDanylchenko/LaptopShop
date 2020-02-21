<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/mainRef.ftlh" as main>
<#setting locale="uk">

<@pad.page "Client">
    <@main.mainPageRef/>

    <div>
        <p><a href="/client/add">Додати нового клієнта</a></p>
    </div>

    <div>
        <p>Знайти клієнта за критеріями:</p>
        <form method="get" action="/client">
            <label>Ім'я:   <input type="text" name="firstName" value="${RequestParameters.firstName!}"/></label>
            <label>Прізвище:   <input type="text" name="secondName" value="${RequestParameters.secondName!}"/></label>
            <label>Дата реєстрації:   <input type="date" name="dateReg" min="2019-02-16" value="${RequestParameters.dateReg!}"></label>
            <button type="submit">Шукати</button>
        </form>
    </div>

    <#if clients?has_content>
        <div><p>Список клієнтів:</p></div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Ім'я</th>
                <th>Прізвище</th>
                <th>Дата реєстрації</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list clients as client>
                <tr>
                    <td>${client.id}</td>
                    <td>${client.firstName}</td>
                    <td>${client.secondName}</td>
                    <td>${client.dateReg}</td>
                    <td><a href="/client/edit/${client.id}">Edit</a></td>
                    <td><a href="/client/delete/${client.id}" onclick="return confirm('Are you sure?')">Delete</a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <h3>Не знайдено жодного клієнта.</h3>
    </#if>
</@pad.page>