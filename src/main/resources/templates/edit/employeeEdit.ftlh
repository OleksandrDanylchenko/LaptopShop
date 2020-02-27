<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "Employee editing">
    <@error.errorOutput errorMessage!/>

    <p class="h1">Зміна інформації співробітника:</p>
    <form method="post" action="/employee/edit/${editEmployee.id}" class="form-group needs-validation w-100" novalidate>
        <div class="form-row my-2">
            <div class="col-6">
                <input type="text" class="form-control" name="firstName"
                       value="${editEmployee.firstName}" placeholder="Ім'я" required/>
                <div class="invalid-feedback">Ім'я не може бути пустим!</div>
            </div>
            <div class="col">
                <input type="text" class="form-control" name="secondName"
                       value="${editEmployee.secondName}" placeholder="Прізвище" required/>
                <div class="invalid-feedback">Прізвище не може бути пустим!</div>
            </div>
        </div>
        <#if shopAddresses?has_content>
            <div class="form-row my-2">
                <div class="col">
                    <select class="custom-select" name="shopAddress">
                        <option value="" selected disabled>Адреса магазину</option>
                        <#list shopAddresses as address>
                            <#if editEmployee.shop??>
                                <option value="${address}"<#if address == editEmployee.shop.address>
                                    selected="selected"</#if>>${address}</option>
                            <#else>
                                <option>${address}</option>
                            </#if>
                        </#list>
                    </select>
                </div>
            </div>
        </#if>

        <div class="form-row my-2">
            <div class="col">
                <#assign isActiveChoises = ['Працюючий', 'Звільнений']>
                    <select class="custom-select" name="isActive">
                        <option value="" selected disabled>Оберіть статус співробітника</option>
                        <#list isActiveChoises as choice>
                            <option value="${choice}"<#if choice == editEmployee.isActive?then("Працюючий", "Звільнений")>
                                selected="selected"</#if>>${choice}</option>
                        </#list>
                    </select>
            </div>
        </div>
        <button type="submit" class="btn btn-danger mt-1 float-right">Зберегти</button>
    </form>
</@pad.page>