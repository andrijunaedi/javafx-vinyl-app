import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class CollectionController {
    @FXML
    private TableView<VinylRecord> inventoryTableView;

    @FXML
    private TableColumn<VinylRecord, String> albumNameColumn;

    @FXML
    private TableColumn<VinylRecord, String> artistColumn;

    @FXML
    private TableColumn<VinylRecord, Integer> availableColumn;
    
    @FXML
    private TableColumn<VinylRecord, Integer> totalColumn;
    
    @FXML
    private TextField rented;

    @FXML
    private TextField title;

    @FXML
    private TextField artist;

    @FXML
    private TextField total;

    private ObservableList<VinylRecord> vinylRecords = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        albumNameColumn.setCellValueFactory(new PropertyValueFactory<>("albumName"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        inventoryTableView.setItems(vinylRecords);
    }


    @FXML
    void handleAdd(ActionEvent event) {
        try {
            String albumName = this.title.getText();
            String artist = this.artist.getText();
            Integer totalVinyl = Integer.parseInt(this.total.getText());
            Integer rentedVinyl = Integer.parseInt(this.rented.getText());

            // Check if an album with the same name already exists
            boolean albumExists = isAlbumExists(albumName);
            boolean isValidTotalAndRented = isTotalAndRentedValid(totalVinyl, rentedVinyl);

            if (!isValidTotalAndRented) {
                throw new IllegalArgumentException();
            }

            if (albumExists) {
                showAlert(Alert.AlertType.WARNING, "Album Sudah Ada", "Album dengan nama \"" + albumName + "\" sudah ada dalam koleksi");
            } else {
                Album newAlbum = new Album(albumName, artist);
                VinylRecord newVinyl = new VinylRecord(newAlbum, totalVinyl - rentedVinyl, totalVinyl);
                vinylRecords.add(newVinyl);
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Album Ditambahkan", "Album baru dengan nama \"" + albumName + "\" berhasil ditambahkan");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Kesalahan Input", "Silakan isi dengan benar total dan jumlah yang disewa");
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Kesalahan Input", "Silakan check data yang ada masukan");
        }
    }

    @FXML
    void handleDelete(ActionEvent event) {
        VinylRecord selectedRecord = inventoryTableView.getSelectionModel().getSelectedItem();
        if (selectedRecord != null) {
            vinylRecords.remove(selectedRecord);
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Album Dihapus", "Album \"" + selectedRecord.getAlbumName() + "\" berhasil dihapus");
        } else {
            showAlert(Alert.AlertType.WARNING, "Tidak Ada Album Terpilih", "Silakan pilih album yang ingin dihapus");
        }
    }

    @FXML
    void handleEdit(ActionEvent event) {
        VinylRecord selectedRecord = inventoryTableView.getSelectionModel().getSelectedItem();
        if (selectedRecord != null) {
            try {
                String albumName = this.title.getText();
                String artist = this.artist.getText();
                Integer totalVinyl = Integer.parseInt(this.total.getText());
                Integer rentedVinyl = Integer.parseInt(this.rented.getText());

                // Check if an album with the same name already exists
                boolean albumExists = isAlbumExists(albumName) && !selectedRecord.getAlbumName().equalsIgnoreCase(albumName);
                boolean isValidTotalAndRented = isTotalAndRentedValid(totalVinyl, rentedVinyl);

                // Validate the input
                if (!isValidTotalAndRented) {
                    throw new IllegalArgumentException();
                }
                if (albumExists) {
                    showAlert(Alert.AlertType.WARNING, "Album Sudah Ada", "Album dengan nama \"" + albumName + "\" sudah ada dalam koleksi");
                    return;
                }
                if (albumName.isEmpty() || artist.isEmpty()) {
                    throw new IllegalArgumentException("Nama album dan artis tidak boleh kosong");
                }
                if (totalVinyl < rentedVinyl) {
                    throw new IllegalArgumentException("Jumlah total vinyl tidak boleh kurang dari jumlah yang disewa");
                }

                selectedRecord.setAlbumName(albumName);
                selectedRecord.setArtist(artist);
                selectedRecord.setTotal(totalVinyl);
                selectedRecord.setAvailable(totalVinyl - rentedVinyl);
                
                inventoryTableView.refresh();
                showAlert(Alert.AlertType.INFORMATION, "Album Diedit", "Album \"" + albumName + "\" berhasil diedit");
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Kesalahan Input", "Silakan isi dengan benar total dan jumlah yang disewa");
            } catch (IllegalArgumentException e) {
                showAlert(Alert.AlertType.ERROR, "Kesalahan Input", "Silakan check data yang ada masukan");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Tidak Ada Album Terpilih", "Silakan pilih album yang ingin diedit");
        }
    }

    @FXML
    void handleMouseClick(MouseEvent event) {
        VinylRecord selectedRecord = inventoryTableView.getSelectionModel().getSelectedItem();
        if (selectedRecord != null) {
            title.setText(selectedRecord.getAlbumName());
            artist.setText(selectedRecord.getArtist());
            total.setText(String.valueOf(selectedRecord.getTotal()));
            rented.setText(String.valueOf(selectedRecord.getAvailable()));
        } else {
            clearFields();
        }
    }

    @FXML
    void handleRentSelected(ActionEvent event) {
        VinylRecord selectedRecord = inventoryTableView.getSelectionModel().getSelectedItem();
        if (selectedRecord != null) {
            if (selectedRecord.getAvailable() > 0) {
                selectedRecord.setAvailable(selectedRecord.getAvailable() - 1);
                inventoryTableView.refresh();
                showAlert(Alert.AlertType.INFORMATION, "Album Disewa", "Album \"" + selectedRecord.getAlbumName() + "\" berhasil disewa");
            } else {
                showAlert(Alert.AlertType.WARNING, "Tidak Tersedia", "Maaf, album \"" + selectedRecord.getAlbumName() + "\" tidak tersedia untuk disewa");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Tidak Ada Album Terpilih", "Silakan pilih album yang ingin disewa");
        }
    }

    private Boolean isAlbumExists(String albumName) {
        for (VinylRecord record : vinylRecords) {
            if (record.getAlbumName().equalsIgnoreCase(albumName)) {
                return true;
            }
        }
        return false;
    }

    private boolean isTotalAndRentedValid(Integer total, Integer rented) {
        return total != null && rented != null && total >= 0 && rented >= 0 && rented <= total;
    }

    private void clearFields() {
        title.clear();
        artist.clear();
        total.clear();
        rented.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
