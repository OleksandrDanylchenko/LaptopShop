<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "HDD editing">
    <@error.errorOutput errorMessage!/>

    <p class="h1">Змінити інформацію про HDD диск:</p>
    <form method="post" action="/hdd/edit/${editHDD.id}" class="form-group needs-validation w-100" novalidate>
        <div class="form-row">
            <input type="text" class="form-control mr-1 mt-2" name="model"
                   value="${editHDD.model!}" placeholder="Модель" required/>
            <div class="invalid-feedback">Назва моделі не може бути пустою!</div>
        </div>
        <div class="form-row">
            <input type="number" min="1" class="form-control mr-1 mt-2" name="memory"
                   value="${(editHDD.memory??)?then(editHDD.memory?c, '')}" placeholder="Пам'ять(GiB)" required/>
            <div class="invalid-feedback">Обсяг пам'яті не може бути пустим!</div>
        </div>
        <button type="submit" class="btn btn-primary my-2 float-right">Зберегти</button>
    </form>
</@pad.page>