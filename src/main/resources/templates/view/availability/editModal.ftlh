<#import "../../errorsHandlers/datesOrderBreaker.ftlh" as dO>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro availabilityEdit id currentLaptopModel currentPrice currentQuantity currentShopAddress
currentDateStart currentDateEnd>
    <div class="modal fade" id="availabilityEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="availabilityEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="availabilityEditLabel${id}">Змінити запис про наявність</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" action="/availability/edit/${id}" id="availabilityEditForm${id}"
                          class="form-group needs-validation w-100"
                          novalidate onsubmit="return validateDates${id}()">
                        <#if laptopModels?has_content>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="laptopModelSelect${id}">Модель ноутбуку:</label>
                                    <select class="custom-select" id="laptopModelSelect${id}" name="editLaptopModel"
                                            required>
                                        <#list laptopModels as model>
                                            <option value="${model}">${model}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не обрано ноутбук!</div>
                                </div>
                            </div>
                        </#if>

                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="priceInput${id}">Ціна:</label>
                                <input type="number" min="5000" max="2147483646"
                                       id="priceInput${id}" class="form-control" name="editPrice"
                                       value="${currentPrice}" required/>
                                <div class="invalid-feedback">Уведено некоректну ціну! (<5000 aбо >2147483646)</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="quantityInput${id}">Кількість:</label>
                                <input type="number" min="1" max="2147483646"
                                       id="quantityInput${id}" class="form-control" name="editQuantity"
                                       value="${currentQuantity}" required/>
                                <div class="invalid-feedback">Уведено некоректну кількість!</div>
                            </div>
                        </div>

                        <#if shopAddresses?has_content>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="shopSelect${id}">Адресу магазину:</label>
                                    <select class="custom-select" id="shopSelect${id}" name="editShopAddress" required>
                                        <#list shopAddresses as address>
                                            <option value="${address}">${address}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не обрано адресу магазину!</div>
                                </div>
                            </div>
                        </#if>

                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="dateStartInput${id}">Дата початку продаж:</label>
                                <input type="date" id="dateStartInput${id}" class="form-control" name="editDateStart"
                                       min="2019-02-16" value="${currentDateStart}" required/>
                                <div class="invalid-feedback">Уведено некоректну дату початку продаж!</div>
                            </div>
                            <div class="col">
                                <label class="d-inline ml-2" for="dateEndInput${id}">Дата закінчення продаж:</label>
                                <input type="date" id="dateEndInput${id}" class="form-control" name="editDateEnd"
                                       min="2019-02-16" value="${currentDateEnd}" required/>
                                <div class="invalid-feedback">Уведено некоректну дату закінчення продаж!</div>
                            </div>
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@fSc.fieldsStatusCleaner modalId="availabilityEditModal${id}" formId="availabilityEditForm${id}"/>
                    <@dO.datesBreaker id="${id}" dateStartId="dateStartInput${id}" dateEndId="dateEndInput${id}"/>
                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $("#availabilityEditModal${id}").on("show.bs.modal", function () {
                            $("#laptopModelSelect${id}").val("${currentLaptopModel}").change();
                            $("#priceInput${id}").val("${currentPrice}").change();
                            $("#quantityInput${id}").val("${currentQuantity}").change();
                            $("#quantityInput${id}").val("${currentQuantity}").change();
                            $("#shopSelect${id}").val("${currentShopAddress}").change();
                            $("#dateStartInput${id}").val("${currentDateStart}").change();
                            $("#dateEndInput${id}").val("${currentDateEnd}").change();
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                    <button type="submit" form="availabilityEditForm${id}" class="btn btn-danger">Змінити</button>
                </div>
            </div>
        </div>
    </div>
</#macro>