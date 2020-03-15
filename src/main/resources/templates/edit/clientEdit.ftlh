<#import "../parts/htmlPad.ftlh" as pad>
<#import "../errorsHandlers/errorOutput.ftlh" as eO>
<#import "../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../errorsHandlers/valueConsistentBreaker.ftlh" as vC>

<@pad.page "Client editing">
    <@eO.errorOutput errorMessage!/>

    <div>
        <div class="h1">Зміна інформації про клієнта:</div>
        <form method="post" action="/client/edit/${editClient.id}" class="form-group needs-validation w-100" novalidate
              onsubmit="return validateForm()">
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="firstName">Ім'я змінюваного покупця:</label>
                    <input type="text" class="form-control" name="firstName"
                           value="${editClient.firstName}" required/>
                    <div class="invalid-feedback">Ім'я змінюваного покупця задано некоректно!</div>
                </div>
                <div class="col">
                    <label class="d-inline ml-2" for="secondName">Прізвище покупця:</label>
                    <input type="text" class="form-control" name="secondName"
                           value="${editClient.secondName}" required/>
                    <div class="invalid-feedback">Прізвище змінюваного покупця задано некоректно!</div>
                </div>
            </div>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="dateReg">Дата реєстрації:</label>
                    <input type="date" class="form-control" name="dateReg" min="2019-02-16"
                           value="${editClient.dateReg?iso_utc}" placeholder="Дата реєстрації" required/>
                    <div class="invalid-feedback">Уведено некоректну дату реєстрації!</div>
                </div>
            </div>
            <button type="submit" class="btn btn-danger float-right">Додати</button>
        </form>
    </div>

    <@eB.emptyBreaker inputNames=["firstName", "secondName"]/>
    <@vC.valueBreaker inputNames=["firstName", "secondName"]/>
</@pad.page>