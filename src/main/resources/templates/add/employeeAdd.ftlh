<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "Employee adding">
    <#if shopAddresses?has_content>
        <@error.errorOutput errorMessage!/>

        <div>
            <div class="h1">Додати нового співробітника:</div>
            <form method="post" action="/employee/add" class="form-group needs-validation w-100" novalidate>
                <div class="form-row my-2">
                    <div class="col">
                        <label class="d-inline ml-2" for="firstName">Ім'я нового співробітника:</label>
                        <input type="text" class="form-control" name="firstName"
                               value="${RequestParameters.firstName!}" required/>
                        <div class="invalid-feedback">Ім'я нового співробітника не може бути пустим!</div>
                    </div>
                    <div class="col">
                        <label class="d-inline ml-2" for="secondName">Прізвище нового співробітника:</label>
                        <input type="text" class="form-control" name="secondName"
                               value="${RequestParameters.secondName!}" required/>
                        <div class="invalid-feedback">Прізвище нового співробітника не може бути пустим!</div>
                    </div>
                </div>
                <div class="form-row my-2">
                    <div class="col">
                        <label class="d-inline ml-2" for="shopAddress">Адреса магазину:</label>
                        <select class="custom-select" name="shopAddress" required>
                            <option value="" selected disabled></option>
                            <#list shopAddresses as address>
                                <option>${address}</option>
                            </#list>
                        </select>
                        <div class="invalid-feedback">Не обрано магазину!</div>
                    </div>
                </div>
                <button type="submit" class="btn btn-danger float-right">Додати</button>
            </form>
        </div>
    <#else>
        <div class="alert alert-warning" role="alert">
            <div class="h1 text-center">Не знайдено жодного активного магазину.</div>
            <div class="h3 text-center"><a href="/shop/add">Додайте новий магазин</a> для створення співробітника.</div>
        </div>
    </#if>
</@pad.page>