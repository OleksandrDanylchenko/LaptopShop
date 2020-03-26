<#import "../parts/htmlPad.ftlh" as pad>
<#import "../errorsHandlers/errorOutput.ftlh" as eO>
<#import "../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../errorsHandlers/valueConsistentBreaker.ftlh" as vC>

<@pad.page "Employee editing">
    <@eO.errorOutput errorMessage!/>

    <p class="h1">Зміна інформації співробітника:</p>
    <form method="post" action="/employee/edit/${editEmployee.id}" class="form-group needs-validation w-100" novalidate
          onsubmit="return validateForm()">
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="firstName">Ім'я змінюваного співробітника:</label>
                <input type="text" class="form-control" name="firstName" value="${editEmployee.firstName!}" required/>
                <div class="invalid-feedback">Ім'я змінюваного співробітника задано некоректно!</div>
            </div>
            <div class="col">
                <label class="d-inline ml-2" for="secondName">Прізвище змінюваного співробітника:</label>
                <input type="text" class="form-control" name="secondName" value="${editEmployee.secondName!}" required/>
                <div class="invalid-feedback">Прізвище змінюваного співробітника задано некоректно!</div>
            </div>
        </div>
        <#if shopAddresses?has_content>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="shopAddress">Адреса магазину:</label>
                    <select class="custom-select" name="shopAddress">
                        <#list shopAddresses as address>
                            <#if editEmployee.shop??>
                                <option value="${address}"<#if address == editEmployee.shop.address>
                                    selected="selected"</#if>>${address}</option>
                            <#else>
                                <option>${address}</option>
                            </#if>
                        </#list>
                    </select>
                    <div class="invalid-feedback">Не обрано магазину!</div>
                </div>
            </div>
        </#if>

        <div class="form-row my-2">
            <div class="col">
                <#assign isActiveChoises = ['Працюючий', 'Звільнений']>
                <label class="d-inline ml-2" for="isActive">Статус співробітника:</label>
                <select class="custom-select" name="isActive">
                    <#list isActiveChoises as choice>
                        <option value="${choice}"<#if choice == editEmployee.isActive?then("Працюючий", "Звільнений")>
                            selected="selected"</#if>>${choice}</option>
                    </#list>
                </select>
            </div>
        </div>
        <button type="submit" class="btn btn-danger float-right">Зберегти</button>
    </form>

    <@eB.emptyBreaker inputIds=["firstName", "secondName"]/>
    <@vC.valueBreaker inputIds=["firstName", "secondName"]/>
</@pad.page>