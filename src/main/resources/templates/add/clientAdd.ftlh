<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "Client adding">
    <@error.errorOutput errorMessage!/>

    <div>
        <div class="h1">Додати нового клієнта:</div>
        <form method="post" action="/client/add" class="form-group needs-validation w-100" novalidate>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="firstName">Ім'я нового покупця:</label>
                    <input type="text" class="form-control" name="firstName"
                           value="${RequestParameters.firstName!}" placeholder="Ім'я нового клієнта"
                           required/>
                    <div class="invalid-feedback">Ім'я нового покупця не може бути пустим!</div>
                </div>
                <div class="col">
                    <label class="d-inline ml-2" for="secondName">Прізвище нового покупця:</label>
                    <input type="text" class="form-control" name="secondName"
                           value="${RequestParameters.secondName!}" placeholder="Прізвище нового клієнта"
                           required/>
                    <div class="invalid-feedback">Прізвище нового покупця не може бути пустим!</div>
                </div>
            </div>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="dateReg">Дата реєстрації:</label>
                    <input type="date" class="form-control" name="dateReg" min="2019-02-16"
                           value="${RequestParameters.dateReg!}" placeholder="Дата реєстрації" required/>
                    <div class="invalid-feedback">Уведено некоректну дату реєстрації!</div>
                </div>
            </div>
            <button type="submit" class="btn btn-danger mt-1 float-right">Додати</button>
        </form>
    </div>
</@pad.page>