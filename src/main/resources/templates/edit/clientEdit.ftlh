<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "Client editing">
    <@error.errorOutput errorMessage!/>

    <div>
        <div class="h1">Зміна інформації про клієнта:</div>
        <form method="post" action="/client/edit/${editClient.id}" class="form-group needs-validation w-100" novalidate>
            <div class="form-row my-2">
                <div class="col-6">
                    <input type="text" class="form-control" name="firstName"
                           value="${editClient.firstName}" placeholder="Ім'я"
                           required/>
                    <div class="invalid-feedback">Ім'я клієнта не може бути пустим!</div>
                </div>
                <div class="col">
                    <input type="text" class="form-control" name="secondName"
                           value="${editClient.secondName}" placeholder="Прізвище"
                           required/>
                    <div class="invalid-feedback">Прізвище клієнта не може бути пустим!</div>
                </div>
            </div>
            <div class="form-row my-2">
                <div class="col">
                    <input type="date" class="form-control" name="dateReg" min="2019-02-16"
                           value="${editClient.dateReg?iso_utc}" placeholder="Дата реєстрації" required/>
                    <div class="invalid-feedback">Уведено некоректну дату реєстрації!</div>
                </div>
            </div>
            <button type="submit" class="btn btn-danger float-right">Додати</button>
        </form>
    </div>
</@pad.page>