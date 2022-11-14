package com.pma.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pma.web.model.Address;
import com.pma.web.repository.AddressRepository;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
	@Autowired
	private AddressRepository addressRepository;
	
	@Override
	public Address addAddress(Address address) {
			return addressRepository.save(address);
	}

	@Override
	public void removeAddress(int addressId) {
		// TODO Auto-generated method stub
		Optional<Address> addressList = this.addressRepository.findByaddressId(addressId);

		if (addressList.isPresent()) {
			this.addressRepository.delete(addressList.get());
		} 
	}

	@Override
	public List<Address> getAllAddress(String postcode) {
		return (List<Address>) addressRepository.findBypostCode(postcode);
	}

	@Override
	public Address getAddress(int addressId) {
		Optional<Address> addressList = this.addressRepository.findByaddressId(addressId);
		if (addressList.isPresent()) {
			return addressList.get();
		} 
		return null;
	}

	@Override
	public Address updateAddress(Address address, int addressId) {
		Optional<Address> addresslist = this.addressRepository.findByaddressId(addressId);
		if (addresslist.isPresent()) {
			Address addressUpdate = addresslist.get();
			addressUpdate.setFlatNo(address.getFlatNo());
			addressUpdate.setHouseNo(address.getHouseNo());
			addressUpdate.setStreet(address.getStreet());
			addressUpdate.setCity(address.getCity());
			addressUpdate.setPostcode(address.getPostcode());
			addressRepository.save(addressUpdate);
			return addressUpdate;
		}
		return null;
	} 
}
