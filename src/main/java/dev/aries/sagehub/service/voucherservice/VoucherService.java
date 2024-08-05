package dev.aries.sagehub.service.voucherservice;

import dev.aries.sagehub.dto.request.AddVoucherRequest;
import dev.aries.sagehub.dto.request.VoucherRequest;
import dev.aries.sagehub.dto.response.GenericResponse;
import dev.aries.sagehub.dto.response.VoucherResponse;
import dev.aries.sagehub.dto.search.GetVouchersPage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The {@code VoucherService} interface provides methods for managing vouchers.
 * It includes functionality for adding vouchers, retrieving a paginated list of vouchers,
 * and verifying the validity of a voucher.
 * <p>
 * Implementations of this interface are responsible for handling the business logic
 * related to voucher operations.
 * </p>
 * @author Jacobs Agyei
 */
public interface VoucherService {

	/**
	 * Adds a collection of vouchers to the system.
	 * <p>
	 * This method takes an {@code AddVoucherRequest} object containing voucher details,
	 * and processes the addition of vouchers to the system.
	 * </p>
	 * @param request the {@code AddVoucherRequest} containing the voucher information.
	 * @return a {@code GenericResponse} object indicating the outcome of the operation.
	 * @throws IllegalArgumentException if the request is null or invalid.
	 */
	GenericResponse addVouchers(AddVoucherRequest request);

	/**
	 * Retrieves a paginated list of vouchers.
	 * <p>
	 * This method takes a {@code GetVouchersPage} request containing filter criteria,
	 * and a {@code Pageable} object for pagination configuration,
	 * and returns a paginated list of vouchers.
	 * </p>
	 * @param request the {@code GetVouchersPage} containing the filter criteria.
	 * @param pageable the {@code Pageable} object specifying the pagination details.
	 * @return a {@code Page<VoucherResponse>} containing the list of vouchers.
	 * @throws IllegalArgumentException if the request or pageable is null or invalid.
	 */
	Page<VoucherResponse> getVouchers(GetVouchersPage request, Pageable pageable);

	/**
	 * Verifies the validity of a voucher.
	 * <p>
	 * This method takes a {@code VoucherRequest} object containing the voucher details,
	 * and performs verification to determine its validity.
	 * </p>
	 * @param request the {@code VoucherRequest} containing the voucher to be verified.
	 * @throws IllegalArgumentException if the request is null or invalid.
	 */
	void verifyVoucher(VoucherRequest request);
}
