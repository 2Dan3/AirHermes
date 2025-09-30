$(document).ready(
                function() {
                    $("#sort").change(
                        function() {
                            var selectedSort = $('#sort').val();
//                            alert('sort: ' + selectedSort);
                            var lokacija = window.location.href;
                            var newlokacija = '';

                            // Update or add sort parameter
                            if (selectedSort != '') {
                                var params = new window.URLSearchParams(window.location.search);
                                var sortParam = params.get('sort');
                                if (sortParam!=null) {
                                    var pozicijaStart = lokacija.indexOf('sort=')+5;
                                    var pozicijaEnd = pozicijaStart+sortParam.length;
                                    var lokacijaStartStr = lokacija.substring(0,pozicijaStart);
                                    var lokacijaEndStr = lokacija.substring(pozicijaEnd);
                                    newlokacija = lokacijaStartStr + selectedSort +lokacijaEndStr;
//                                    newlokacija = newlokacija.replace('sort=' + sortParam, 'sort=' + selectedSort);
                                } else {
                                    if (lokacija.indexOf('?')!=-1) {
                                        newlokacija = lokacija + '&sort=' + selectedSort;
                                    } else {
                                        newlokacija = lokacija + '?sort=' + selectedSort;
                                    }
                                }
                            }

                            // Replace the window location with the new URL if anything was updated
                            if (newlokacija !== lokacija) {
                                window.location.replace(newlokacija);
                            }
                        });
                    });
