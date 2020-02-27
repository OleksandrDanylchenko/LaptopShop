<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "Employee adding">
    <#if shopAddresses?has_content>
        <@error.errorOutput errorMessage!/>

        <div>
            <h1>Додати нового співробітника:</h1>
            <form method="post" action="/employee/add" class="form-group needs-validation w-100" novalidate>
                <div class="form-row my-2">
                    <div class="col-6">
                        <input type="text" class="form-control" name="firstName"
                               value="${RequestParameters.firstName!}" placeholder="Ім'я нового співробітника"
                               required/>
                        <div class="invalid-feedback">Ім'я нового співробітника не може бути пустим!</div>
                    </div>
                    <div class="col">
                        <input type="text" class="form-control" name="secondName"
                               value="${RequestParameters.secondName!}" placeholder="Прізвище нового співробітника"
                               required/>
                        <div class="invalid-feedback">Прізвище нового співробітника не може бути пустим!</div>
                    </div>
                </div>
                <div class="form-row my-2">
                    <div class="col">
                        <select class="custom-select" name="shopAddress">
                            <option value="" selected disabled>Адреса магазину</option>
                            <#list shopAddresses as address>
                                <option>${address}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <button type="submit" class="btn btn-danger mt-1 float-right">Додати</button>
            </form>
        </div>
    <#else>
        <div class="alert alert-warning" role="alert">
            <div class="h1 text-center">Не знайдено жодного активного магазину.</div>
            <div class="h3 text-center"><a href="/shop/add">Додайте новий магазин</a> для створення співробітника.</div>
        </div>
    </#if>
</@pad.page>