<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Employee editing">
    <h2>${errorMessage!}</h2>

    <h1>Зміна інформації співробітника:</h1>
    <form method="post" action="/employee/edit/${editEmployee.id}">
        <label>Ім'я: <input type="text" name="firstName" value="${editEmployee.firstName}"></label>
        <label>Прізвище: <input type="text" name="secondName" value="${editEmployee.secondName}"></label>
        <#if shopAddresses?has_content>
            <label>Адреса магазину:
                <select name="shopAddress">
                    <#list shopAddresses as address>
                        <#if editEmployee.shop??>
                            <option value="${address}"<#if address == editEmployee.shop.address>
                                selected="selected"</#if>>${address}</option>
                        <#else>
                            <option>${address}</option>
                        </#if>
                    </#list>

                </select>
            </label>
        </#if>


        <#assign isActiveChoises = ['Так', 'Ні']>
        <label>Працюючий:
            <select name="isActive">
                <#list isActiveChoises as choice>
                    <option value="${choice}"<#if choice == editEmployee.isActive?then("Так", "Ні")>
                        selected="selected"</#if>>${choice}</option>
                </#list>
            </select>
        </label>
        <button type="submit">Зберегти</button>
    </form>
</@pad.page>