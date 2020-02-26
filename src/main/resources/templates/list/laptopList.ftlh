<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Laptop">
    <div class="row">
        <div class="col">
            <a class="btn btn-outline-primary " href="/laptop/add" role="button">
                <i class="far fa-plus-square"></i> Додати новий ноутбук</a>
        </div>

        <div class="col-lg-3 col-4">
            <button class="btn btn-primary w-100" type="button" data-toggle="collapse"
                    data-target="#laptopSearch" aria-expanded="false" aria-controls="laptopSearch">
                Знайти ноутбук
            </button>
            <div class="collapse form-group" id="laptopSearch">
                <form method="get" action="/laptop">
                    <input type="text" class="form-control mr-1 mt-2" name="labelBrand"
                           value="${RequestParameters.labelBrand!}" placeholder="Бренд"/>
                    <input type="text" class="form-control mr-1 mt-2" name="labelModel"
                           value="${RequestParameters.labelModel!}" placeholder="Модель"/>
                    <input type="text" class="form-control mr-1 mt-2" name="typeName"
                           value="${RequestParameters.typeName!}" placeholder="Тип"/>
                    <input type="text" class="form-control mr-1 mt-2" name="hardwareAssemblyName"
                           value="${RequestParameters.hardwareAssemblyName!}" placeholder="Назва збірки"/>
                    <button type="submit" class="btn btn-primary ml-md-2 my-2 float-right">
                        <i class="fa fa-search"></i>
                    </button>
                </form>
            </div>
        </div>
    </div>

    <div class="row mt-5">
        <#if laptops?has_content>
            <div class="h2">Список ноутбуків:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Бренд</th>
                    <th scope="col">Модель</th>
                    <th scope="col">Тип</th>
                    <th scope="col">Збірка</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list laptops as laptop>
                    <tr>
                        <td scope="row">${laptop.id}</td>
                        <td>${laptop.label.brand}</td>
                        <td>${laptop.label.model}</td>
                        <td><#if laptop.type??>${laptop.type.name}<#else>Відсутній</#if></td>
                        <td><#if laptop.hardware??>${laptop.hardware.assemblyName}<#else>Відсутній</#if></td>
                        <td><a class="btn btn-outline-primary" href="/laptop/edit/${laptop.id}"
                               role="button"><i class="fa fa-edit"></i></a></td>
                        <td><a class="btn btn-outline-danger" href="/laptop/delete/${laptop.id}"
                               role="button" onclick="return confirm('Ви впевнені?')"><i class="fa fa-trash"></i></a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодного ноутбуку.</div>
        </#if>
    </div>
</@pad.page>