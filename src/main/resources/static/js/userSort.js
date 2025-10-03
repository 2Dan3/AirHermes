$(document).ready(
                function() {
                    $("#sort").change(
                        function() {
                            var selectedUserSort = $('#sort').val();
//                            alert('sort: ' + selectedUserSort);
                            var loc = window.location.href;
                            var newloc = '';

                            // Update or add sort parameter
                            if (selectedUserSort != '') {
                                var params = new window.URLSearchParams(window.location.search);
                                var sortParam = params.get('sort');
                                if (sortParam!=null) {
                                    var pozicijaPocetak = loc.indexOf('sort=')+5;
                                    var pozicijaKraj = pozicijaPocetak+sortParam.length;
                                    var locStartStr = loc.substring(0,pozicijaPocetak);
                                    var locEndStr = loc.substring(pozicijaKraj);
                                    newloc = locStartStr + selectedUserSort +locEndStr;
//                                    newloc = newloc.replace('sort=' + sortParam, 'sort=' + selectedUserSort);
                                } else {
                                    if (loc.indexOf('?')!=-1) {
                                        newloc = loc + '&sort=' + selectedUserSort;
                                    } else {
                                        newloc = loc + '?sort=' + selectedUserSort;
                                    }
                                }
                            }

                            // Replace the window location with the new URL if anything was updated
                            if (newloc !== loc) {
                                window.location.replace(newloc);
                            }
                        });
                    });
