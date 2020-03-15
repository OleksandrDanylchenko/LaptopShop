<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorsHandlers/errorOutput.ftlh" as eO>
<#import "../parts/errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../parts/errorsHandlers/valueConsistentBreaker.ftlh" as vC>

<@pad.page "Client adding">
    <@eO.errorOutput errorMessage!/>

    <div>
        <div class="h1">Додати нового клієнта:</div>
        <form method="post" action="/client/add" class="form-group needs-validation w-100" novalidate
              modelAttribute="newClient" onsubmit="return validateForm()">
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="firstName">Ім'я нового покупця:</label>
                    <input type="text" class="form-control" name="firstName"
                           value="${RequestParameters.firstName!}" required/>
                    <div class="invalid-feedback">Ім'я нового покупця задано некоректно!!</div>
                </div>
                <div class="col">
                    <label class="d-inline ml-2" for="secondName">Прізвище нового покупця:</label>
                    <input type="text" class="form-control" name="secondName"
                           value="${RequestParameters.secondName!}" required/>
                    <div class="invalid-feedback">Прізвище нового покупця задано некоректно!!</div>
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

    <@eB.emptyBreaker inputNames=["firstName", "secondName"]/>
    <@vC.valueBreaker inputNames=["firstName", "secondName"]/>
</@pad.page>