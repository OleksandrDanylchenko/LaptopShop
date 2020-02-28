<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "RAM editing">
    <@error.errorOutput errorMessage!/>

    <div class="h1">Змінити інформацію про оперативну пам'ять:</div>
    <form method="post" action="/ram/edit/${editRam.id}" class="form-group needs-validation w-100" novalidate>
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="model">Назва моделі:</label>
                <input type="text" class="form-control" name="model"
                       value="${editRam.model!}" required/>
                <div class="invalid-feedback">Назва моделі не може бути пустою!</div>
            </div>
        </div>
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="memory">Обсяг пам'яті (GiB):</label>
                <input type="number" min="1" class="form-control" name="memory"
                       value="${(editRam.memory??)?then(editRam.memory?c, '')}" required/>
                <div class="invalid-feedback">Обсяг пам'яті не може бути пустим!</div>
            </div>
        </div>
        <button type="submit" class="btn btn-danger my-2 float-right">Зберегти</button>
    </form>
</@pad.page>